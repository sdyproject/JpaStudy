import axios from "axios";
import { useState } from "react";
import styles from './MemberJoin.module.scss'
import { useNavigate } from "react-router-dom";


function MemberJoin  () {
    
    const Navigate = useNavigate();

    const [member,setMember] = useState ({
        username:"",
        name:"",
        password:"",
        hp:"",
        birth:"",
    });

    const changValue = (e) => {
    setMember({
    ...member,
    [e.target.name]:e.target.value
    });
    }
    
   
    
    const joinmember = (e) => {
        e.preventDefault();
        axios({
            url:'api/member',
            method:'POST',
            headers: {'Content-Type': 'application/json; charset=utf-8',
                // access : localStorage.getItem("access")
            },
            data:JSON.stringify(member)
        }).then(res=>{
            if (res.status === 200 ) {
                alert("회원 가입이 완료 되었습니다.");
                Navigate('/');
            }
            console.log(res);

        })
        .catch(error => {
            alert("이미 존재하는 아이디 입니다.")
            console.log(error.res);
        }); 
    }

    

    return (
        <div className={styles.memberjoin}>
        <form className={styles.memberjoin__form} onSubmit={joinmember} >
        <div className={styles.wrapper}>
                <span className={styles.wrapper__title}>회원 가입</span> <br/>
                 <div className={styles.wrapper__content}>
                    <input type="text" placeholder="아이디" name="username"  onChange={changValue} className={styles.wrapper__content__input} /> <br/>
                    <input type="text" placeholder="이름" name="name"  onChange={changValue} className={styles.wrapper__content__input} /> <br/>
                    <input type="text" placeholder="비밀번호" name="password"  onChange={changValue} className={styles.wrapper__content__input} /> <br/>
                    <input type="text" placeholder="연락처" name="hp"  onChange={changValue}  className={styles.wrapper__content__input} /> <br/>
                    <input type="date"  name="birth"  onChange={changValue}  className={styles.wrapper__content__input}/>
                 </div>
                </div>
 
                <div className={styles.memberjoin__submit}>
                    <button className={styles.memberjoin__submit__button} type="submit">회원가입</button>
                </div>

        
                </form>
        </div>


    );
    
}

export default MemberJoin;