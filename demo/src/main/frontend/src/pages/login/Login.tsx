
import { useNavigate } from "react-router-dom";
import styles from './Login.module.scss'
import axios from "axios";
import { useState } from "react";

function Login() {
    const Navigate = useNavigate();

    const [login,setLogin] = useState ({
      username:"",
      password:"",
    });

    const changValue = (e) => {
      setLogin({
      ...login,
      [e.target.name]:e.target.value
      });
    }

const loginmember = (e) => {
  e.preventDefault();
  
  axios({
      url:'api/login',
      method:'POST',
      headers: {'Content-Type': 'application/json; charset=utf-8'},
      withCredentials: true, 
      data:JSON.stringify(login)
  })
  .then(res=>{

      if(res.status === 200) {
        // const  access = res.headers.access;
        // console.log('access:',access);
        // localStorage.setItem('access',access);
        
        alert("로그인 되었습니다.");
        Navigate('/');
      }
      
      console.log(res);

  })
  .catch(error => {
      alert("로그인 실패");
      console.log(error.res);
  })
}


  return (
    
    <div className={styles.login}>
       
      <form className={styles.login__form}  onSubmit={loginmember}>
          <div className={styles.wrapper}>
            <span className={styles.wrapper__title}>로그인</span> <br/>
                <div className={styles.wrapper__content}>
                    <input type="text" placeholder="아이디" name="username" onChange={changValue} className={styles.wrapper__content__input}/> <br/>
                    <input type="text" placeholder="비밀번호" name="password" onChange={changValue} className={styles.wrapper__content__input} /> <br/>
                   
                 </div>

          </div>
          <div className={styles.login__submit}>
                    <button className={styles.login__submit__button} type="submit">로그인</button>
                </div>
      </form>

    </div>
  )
}

export default Login