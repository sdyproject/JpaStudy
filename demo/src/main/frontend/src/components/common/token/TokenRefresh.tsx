
// import { useNavigate } from "react-router-dom"
// import { useEffect } from "react";
// import axios from "axios";
// export default function TokenRefresh(){
//     const navigate =useNavigate();


//     useEffect(() => {
//         const refreshApi = axios.create({
//             baseURL: 'http://localhost:5173/api',
//             headers: {"access": "access"},
//             //쿠키 
//             withCredentials: true, 
//         });


//         const interceptor = axios.interceptors.response.use(
//             function (response) {
//                 return response;

//             },
//             async function (error) {
//                 const progressConig = error.config;
//                 const msg =  error.response.data.message;
//                 const status = error.response.status;

//                 if(status == 401) {
//                     if(msg == "access token expired"){
//                         await axios({
//                             url: '/reissue',
//                             method:'POST',
//                             headers : {
//                                 refresh: document.cookie
//                             },
//                         }).then((res) => {
//                             localStorage.setItem("access", res.data.access);

//                             progressConig.headers["access"]=res.data.access;

//                             return refreshApi(progressConig);
//                         })
//                         .then(() => {
//                            window.location.reload(); 
//                         });

//                     }else if(msg == "refresh token expired"){
//                         console.error('Refresh 토큰 만료:', error);
//                         alert("세션이 만료되었습니다. 다시 로그인해주세요.");
                        
//                         navigate('/login'); 
//                     }
//                 }
//                 return Promise.reject(error); // 다른 오류는 그대로 반환
//             }
//         );
//         return () => {
//             // 컴포넌트 언마운트 시 인터셉터 제거
//             axios.interceptors.response.eject(interceptor);
//         };
//     })
// }
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import axios from "axios";

export default function TokenRefresh() {
    const navigate = useNavigate();

    useEffect(() => {
        // 새로운 axios 인스턴스 생성
        const refreshApi = axios.create({
            baseURL: 'http://localhost:5173/api',
            headers: { "access": "access" },
            withCredentials: true, // 쿠키 사용을 위해 설정
        });

        // 응답 인터셉터 설정
        const interceptor = axios.interceptors.response.use(
            function (response) {
                // 응답 성공 시 그대로 반환
                return response;
            },
            async function (error) {
                const progressConfig = error.config;
                const msg = error.response.data.message;
                const status = error.response.status;

                if (status === 401) { // 401 Unauthorized 응답 처리
                    if (msg === "access token expired") { // Access 토큰 만료
                        try {
                            // 토큰 재발급 요청
                            const res = await axios({
                                url: '/api/reissue',
                                method: 'POST',
                                headers: { refresh: document.cookie } // 쿠키에서 Refresh 토큰 가져오기
                            });
                            // 새로운 Access 토큰을 로컬 스토리지에 저장
                            localStorage.setItem("access", res.data.access);
                            // 재요청 설정에 새로운 Access 토큰 추가
                            progressConfig.headers["access"] = res.data.access;
                            // 재발급 받은 토큰으로 원래 요청 재시도
                            return refreshApi(progressConfig);
                        } catch (err) {
                            console.error('토큰 재발급 실패:', err);
                            // Refresh 토큰이 만료되었을 경우 로그인 페이지로 이동
                            navigate('/login'); 
                        }
                    } else if (msg === "refresh token expired") { // Refresh 토큰 만료
                        console.error('Refresh 토큰 만료:', error);
                        alert("세션이 만료되었습니다. 다시 로그인해주세요.");
                        // 로그인 페이지로 이동
                        navigate('/login'); 
                    }
                }
                return Promise.reject(error); // 다른 오류는 그대로 반환
            }
        );

        return () => {
            // 컴포넌트 언마운트 시 인터셉터 제거
            axios.interceptors.response.eject(interceptor);
        };
    }, [navigate]);

    return null;
}