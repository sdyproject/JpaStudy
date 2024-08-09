import styles from './CommonSearchBar.module.scss'
import { Link } from 'react-router-dom';

import axios from 'axios';
import { Cookies } from 'react-cookie';


 const cookies = new Cookies();

 function CommonSearchBar() {
  
  const accesstoken = localStorage.getItem('access');

  


   // 로그아웃 함수
   const handleLogout = async () => {
    try{
      const refreshToken = cookies.get('refresh');
      const response = await axios.post('api/logout', {refresh : refreshToken}
      );
      if (response.status === 200) {
        alert("로그아웃 완료");
        localStorage.removeItem('access');
        cookies.remove(refreshToken);
        window.location.href = '/login';
        
      }
     
      
    }catch(error) {
      alert("로그아웃 완료");
      const refreshToken = cookies.get('refresh');
      localStorage.removeItem('access');
      cookies.remove(refreshToken);
      window.location.href = '/login';
    }
    
      
    
  }


  return (
   <div className={styles.searchBar}>
    <div className={styles.searchBar__search}>
        <img src="/src/assets/icons/icon-search.svg"  className={styles.searchBar__search__img} />
        <input type="text"  placeholder='Search ' className={styles.searchBar__search__input}/>
        
    </div>
    <div className={styles.searchBar__memberdata}
>
    {accesstoken ? (
      <span className={styles.searchBar__memberdata__memberName}></span>
    ) : (
      <span className={styles.searchBar__memberdata__memberName}></span>
    )}
    </div>
    <div className={styles.searchBar__memberfcn}>
    {accesstoken ? (
          <button onClick={handleLogout} className={styles.searchBar__memberfcn__button}>LOGOUT</button>
        ) : (
          <Link to="/login" className={styles.searchBar__memberfcn__button}>LOGIN</Link>
        )}

    {accesstoken ? (
          <></>
        ) : (
          <Link to="/member" className={styles.searchBar__memberfcn__button}>JOIN</Link>

        )}  
    </div>
   </div>
  )
}

export default CommonSearchBar
