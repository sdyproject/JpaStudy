import axios,{InternalAxiosRequestConfig, AxiosResponse} from 'axios';
import { Cookies } from 'react-cookie';


const cookies = new Cookies();

// axios 인스턴스 생성 (기본 설정 포함)
const refreshApi = axios.create({
  baseURL: 'http://localhost:5173/',  // 기본 API URL 설정
  withCredentials:true,
});

// 요청 인터셉터 설정 (request interceptor)
refreshApi.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // localStorage에서 accessToken 가져오기
    const accessToken = localStorage.getItem('access');
    if (accessToken) {
      // accesstoken 이 존재하면 access 헤더에 추가
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

    // 에러 응답이 401이고, 재시도 플래그가 설정되지 않은 경우
    if (error.response.status === 401 && !originalRequest._retry) { 
      originalRequest._retry = true;
      try {
        // cookis에서 refreshToken 가져오기
        const refreshToken = cookies.get('refresh');
       
        // refresh token으로  newAccessToken, newrefreshtoken 요청             
        const response = await axios.post('api/reissue', { refresh : refreshToken });

        // 응답에서 newAccessToken과 newrefreshtoken 추출
        const newAccessToken = response.headers['access'];
        const newRefreshToken = cookies.get('refresh');
        
         // newnewAccessToken으로 localStorage에 있던 토큰과 변경
        if (newAccessToken) {
          localStorage.setItem('access', newAccessToken);
        }
         // // newrefreshtoken으로 cookis 변경
        if (newRefreshToken) {
          cookies.set('refresh', newRefreshToken);
        }



        originalRequest.headers['access'] = `${newAccessToken}`;
        return refreshApi(originalRequest);
      } catch (err) {
        const refreshToken = cookies.get('refresh');
        alert("refresh 토큰 만료 : 로그인 시간이 만료되었습니다. 다시 로그인 해주세요.");
        localStorage.removeItem('access');
        cookies.remove(refreshToken);
        window.location.href = '/login';
       
        // const refreshToken = cookies.get('refresh');
        // // console.error('refresh token 만료 요청 실패', err);
        // // // refresh token 만료시 로그아웃 
        // // await axios.post('api/logout', { refresh : refreshToken},{
        // // withCredentials: true
        // // });
        
        
        // alert("access 토큰 만료 : 로그인 시간이 만료되었습니다. 다시 로그인 해주세요.");
        // localStorage.removeItem('access');
        // cookies.remove(refreshToken);
        // window.location.href = '/login';

        return Promise.reject(err);
      }
    }else if(error.response.status === 400) {
       const refreshToken = cookies.get('refresh');
         alert("refresh 토큰 만료 : 로그인 시간이 만료되었습니다. 다시 로그인 해주세요.");
         localStorage.removeItem('access');
         cookies.remove(refreshToken);
         window.location.href = '/login';
     }

   
   
  }
  // return Promise.reject(error);
);


export default refreshApi;
