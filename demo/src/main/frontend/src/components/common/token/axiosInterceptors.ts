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
    // localStorage에서 accessToken 가져오기
    const refreshToken = cookies.get('refresh');
    if (refreshToken) {
      // accesstoken 이 존재하면 access 헤더에 추가
      config.headers['refresh'] = `${refreshToken}`;
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
    
    // 에러 응답이 401(Unauthorized)이고, 재시도 플래그가 설정되지 않은 경우
    if (error.response.status === 400 ) { 
      try {
        const refreshToken = cookies.get('refresh');
        alert("refresh 토큰 만료 : 로그인 시간이 만료되었습니다. 다시 로그인 해주세요.");
        localStorage.removeItem('access');
        cookies.remove(refreshToken);
        window.location.href = '/login';

        
      } catch (err) {
        
        return Promise.reject(err);
      }
    }

   
    return Promise.reject(error);
  }
);

  export {refreshApi };