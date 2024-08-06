import styles from './CommonHeader.module.scss'
import { Link } from 'react-router-dom';
// import { Cookies } from 'react-cookie';
// import refreshApi from "../../../components/common/token/refreshApi";
// import { useNavigate } from "react-router-dom";

// const cookies = new Cookies()

function CommonHeader() {
  // const navigate = useNavigate();
   const accesstoken = localStorage.getItem('access');

  // 로그아웃 함수
  // const Logout = async () => {
  //   try {
  //     // 서버에 로그아웃 요청 보내기
  //    const response = await refreshApi.post('api/logout', { refresh: refreshToken },{
  //       withCredentials:true
  //     });
  //     if (response.status === 200) {
  //       alert("로그아웃 완료");
  //       navigate('/');
  //     }
  //     // 로컬 스토리지에서 토큰 제거
  //     localStorage.removeItem('access')
  //     cookies.remove(refreshToken);

  //     // 로그아웃 후 새로고침 또는 다른 행동
     
  //   } catch (error) {
  //     console.error('Logout failed', error);
  //     // 에러 핸들링 추가 (예: 사용자에게 알림)
  //   }
  // };
  
  return (
    <div className={styles.header}>
      <div className={styles.header__profileBox}>
              <Link to="/" className={styles.header__profileBox__button}>Home</Link>
              <Link to="/board" className={styles.header__profileBox__button}>BOARD</Link>
              <Link to="/" className={styles.header__profileBox__button}>MATE</Link>
          </div>
        <Link to="/" className={styles.header__logoBox}>
            <img src="/src/assets/images/image-logo.png" alt="logo" className={styles.header__logoBox__logo} />
            <span className={styles.header__logoBox__title}>PLANNABLE</span>
        </Link>
            <div className={styles.header__profileBox}>           
                <Link to="/member" className={styles.header__profileBox__button}>SCHEDULE</Link>
                <Link to="/member" className={styles.header__profileBox__button}>JOIN</Link>
      {accesstoken ? (
                       <Link to="/member" className={styles.header__profileBox__button}>MY</Link>
                      ) : (
                        <></>) }
            </div>
    </div>
    
  )
}

export default CommonHeader