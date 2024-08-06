import styles from './CommonSearchBar.module.scss'
import { Link } from 'react-router-dom';
import refreshApi from "../../../components/common/token/refreshApi";
// import { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom"; 
// import axios from 'axios';
// import { Cookies } from 'react-cookie';

// const cookies = new Cookies();

 function CommonSearchBar() {
  // const [memberName, setMemberName] = useState('');
  const navigate = useNavigate();
  const accesstoken = localStorage.getItem('access');
  // const refreshToken = cookies.get('refresh');


  // useEffect(() => {
  //   const fetchMemberData = () => {
  //   refreshApi.get('api/current-member')
  //     .then(response => {
  //       if (response.status === 200) {
  //         setMemberName(response.data); 
  //       }
  //     }).catch (error =>{
  //       console.error('Error fetching member data:', error);
       
  //     }) 
       
      
  //   };

  //   fetchMemberData();
  // }, []);
  


   // 로그아웃 함수
   const handleLogout = async () => {
    try{
      const response = await refreshApi.post('api/logout');
      if (response.status === 200) {
        alert("로그아웃 완료");
        localStorage.removeItem('access');
        navigate('/');
        
      }
     
      
    }catch(error) {
      console.error('Logout failed', error);
    }
    //  refreshApi.post('api/logout', { refresh: refreshToken }, {
    //     // withCredentials: true
    //   }).then(response => {
    //     if (response.status === 200) {
    //       alert("로그아웃 완료");
    //       navigate('/');
    //     }
    //     localStorage.removeItem('access');
    //     cookies.remove('refresh');
    //   }).catch (error =>{
    //     console.error('Logout failed', error);
    //   }) 
      
    
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
          <button onClick={handleLogout} className={styles.searchBar__memberfcn__button}>logout</button>
        ) : (
          <Link to="/login" className={styles.searchBar__memberfcn__button}>login</Link>
        )}
    </div>
   </div>
  )
}

export default CommonSearchBar
