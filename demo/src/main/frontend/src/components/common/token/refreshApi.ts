// import { useNavigate } from "react-router-dom";
// import { useEffect } from "react";
// import axios from "axios";
// import { config } from "localforage";

// export default function TokenRefresh() {

   
//      const navigate = useNavigate();
//      const refreshApi = axios.create({
//          baseURL: 'http://localhost:5173'
//     });


    // refreshApi.interceptors.request.use(
    //     (config) => {
    //       // 로컬 스토리지에서 액세스 토큰 가져오기
    //       const accessToken = localStorage.getItem('access');
    //       if (accessToken) {
    //         // 액세스 토큰이 존재하면 access 헤더에 추가
    //         config.headers['access'] = `${accessToken}`;
    //       }
    //       // 수정된 설정 반환
    //       return config;
    //     },
    //     (error) => {
    //       // 요청 에러 처리
    //       return Promise.reject(error);
    //     }
    //   );

     // useEffect(() => {
    //     // 새로운 axios 인스턴스 생성
    //     const refreshApi = axios.create({
    //         baseURL: 'http://localhost:5173',
    //         headers: {
    //             "Content-Type": "application/json",
    //         },
            
    //     });
        

    //     // 응답 인터셉터 설정
    //      axios.interceptors.response.use(
    //         function (response) {
    //             // 응답 성공 시 그대로 반환
    //             return response;
    //         },
    //         async function (error) {
    //             const progressConfig = error.config;
    //             // const msg = error.response.data.message;
    //             const status = error.response.status;

    //             if (status === 401) { // 401 Unauthorized 응답 처리
    //                 // if (msg === "access token expired") { // Access 토큰 만료
    //                     try {
    //                         // 토큰 재발급 요청
    //                         const res = await axios({
    //                             url: '/api/reissue',
    //                             method: 'POST',
    //                             // withCredentials: true,
    //                             headers: { 
    //                                 refresh: document.cookie }, // 쿠키에서 Refresh 토큰 가져오기
    //                         });
    //                         // 새로운 Access 토큰을 로컬 스토리지에 저장
    //                         localStorage.setItem("access", res.data.access);
    //                         // 재요청 설정에 새로운 Access 토큰 추가
    //                         progressConfig.headers["access"] = res.data.access;
    //                         // 재발급 받은 토큰으로 원래 요청 재시도
    //                         return refreshApi(progressConfig);
    //                     } catch (err) {
    //                         console.error('토큰 재발급 실패:', err);
    //                         // Refresh 토큰이 만료되었을 경우 로그인 페이지로 이동
    //                         navigate('/login'); 
    //                     }
    //                 // } else if (msg === "refresh token expired") { // Refresh 토큰 만료
    //                 //     console.error('Refresh 토큰 만료:', error);
    //                 //     alert("세션이 만료되었습니다. 다시 로그인해주세요.");
    //                 //     // 로그인 페이지로 이동
    //                 //     navigate('/login'); 
    //                 // }
    //             }
    //             return Promise.reject(error); // 다른 오류는 그대로 반환
    //         }
    //     );

    //     return () => {
    //         // 컴포넌트 언마운트 시 인터셉터 제거
    //         axios.interceptors.response.eject(interceptor);
    //     };
    // }, [navigate]);

    // return null;
// }

import axios,{InternalAxiosRequestConfig, AxiosResponse} from 'axios';
import { Cookies } from 'react-cookie';

const cookies = new Cookies();

// axios 인스턴스 생성 (기본 설정 포함)
const refreshApi = axios.create({
  baseURL: 'http://localhost:5173/', // 기본 API URL 설정
});

// 요청 인터셉터 설정 (request interceptor)
refreshApi.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 로컬 스토리지에서 액세스 토큰 가져오기
    const accessToken = localStorage.getItem('access');
    if (accessToken) {
      // 액세스 토큰이 존재하면 access 헤더에 추가
      config.headers['access'] = `${accessToken}`;
    }
    // 수정된 설정 반환
    return config;
  },
  (error) => {
    // 요청 에러 처리
    return Promise.reject(error);
  }
);

// 응답 인터셉터 설정 (response interceptor)
refreshApi.interceptors.response.use(
  (response : AxiosResponse) => {
    // 성공적인 응답일 경우 그대로 반환
    return response;
  },
  async (error) => {
    // 에러 응답 중 원래 요청 정보 가져오기
    const originalRequest = error.config;
    // 에러 응답이 401(Unauthorized)이고, 재시도 플래그가 설정되지 않은 경우
    if (error.response.status === 401 && !originalRequest._retry) { 
      originalRequest._retry = true;
      try {
        // 쿠키에서 리프레시 토큰 가져오기
        const refreshToken = cookies.get('refresh');
       
        // 리프레시 토큰으로 새 액세스 토큰 요청             
        const response = await axios.post('api/reissue', { refresh : refreshToken });

        // 응답에서 새로운 액세스 토큰과 리프레시 토큰 추출
        const newAccessToken = response.headers['access'];
        const newRefreshToken = cookies.get('refresh');
        
       
        if (newAccessToken) {
          localStorage.setItem('access', newAccessToken);
        }

        if (newRefreshToken) {
          cookies.set('refresh', newRefreshToken);
        }

        // 새 액세스 토큰을 로컬 스토리지에 저장
        // localStorage.setItem('access', accessToken);
        // // 새 리프레시 토큰을 쿠키에 저장
        // cookies.set('refresh', newRefreshToken);

        originalRequest.headers['access'] = `${newAccessToken}`;
        return refreshApi(originalRequest);
      } catch (err) {
        // 리프레시 토큰 요청 실패 시 콘솔에 에러 출력
        console.error('refresh token 만료 요청 실패', err);
        // 실패 처리 (예: 사용자 로그아웃)
        
        await axios.post('api/logout', {}, {
          withCredentials:true
        })

        localStorage.removeItem('access');

        alert("로그인 시간이 만료되었습니다. 다시 로그인 해주세요.");

        window.location.href = '/login';
        
        return Promise.reject(err);
      }
    }

    // 다른 에러는 그대로 반환
    return Promise.reject(error);
  }
);

// 설정이 완료된 axios 인스턴스 내보내기
export default refreshApi;
