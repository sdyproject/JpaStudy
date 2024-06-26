import axios from "axios";

import { useNavigate } from "react-router-dom";
import styles from './Board.module.scss'
import { useState } from 'react';
function Board() {

    const Navigate = useNavigate();

    const [board,setBoard] = useState ({
        boardname:"",
        boardcontext:"",
        boardschedule:"",
    });

    const changValue = (e) => {
        setBoard({
    ...board,
    [e.target.name]:e.target.value
    });
    }
    
   
    
    const boradsubmit = (e) => {
        e.preventDefault();
        axios({
            url:'api/board',
            method:'POST',
            headers: {'Content-Type': 'application/json; charset=utf-8',
                access : localStorage.getItem("access")
            },
            withCredentials: true,
            data:JSON.stringify(board)
        }).then(res=>{
            if (res.status === 200 ) {
                alert("작성 완료");
                Navigate('/');
            }
            console.log(res);

        })
         .catch(error => {
         alert("작성 실패")
             console.log(error.res);
         }); 
    }


  return (
     <div className={styles.writeborad}>
    <form className={styles.writeborad__form} onSubmit={boradsubmit} >
    <div className={styles.wrapper}>
            <span className={styles.wrapper__title}>제목</span> <br/>
             <div className={styles.wrapper__content}>
                <input type="text" placeholder="게시글 제목" name="boardname"  onChange={changValue} className={styles.wrapper__content__input} /> <br/>
                <input type="text" placeholder="내용" name="boardcontext"  onChange={changValue} className={styles.wrapper__content__input} /> <br/>
                <input type="date" placeholder="연락처" name="boardschedule"  onChange={changValue}  className={styles.wrapper__content__input} /> <br/>
             </div>
            </div>

            <div className={styles.writeborad__submit}>
                <button className={styles.writeborad__submit__button} type="submit">작성</button>
            </div>

    
            </form>
    </div>

  )
}

export default Board
