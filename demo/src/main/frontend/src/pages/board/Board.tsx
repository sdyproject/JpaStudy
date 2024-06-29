import { useNavigate } from "react-router-dom";
import styles from './Board.module.scss'
import { useState } from 'react';
import refreshApi from "../../components/common/token/refreshApi";
function Board() {

    const navigate = useNavigate();

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
    
   
    
     const boradsubmit = async (e) => {
        // interceptors 적용 전 로그인 시 localStorage에 저장된 access값 가져오기 
        
        // const accesstoken = localStorage.getItem("access");
        e.preventDefault();
        try{
            const response = await refreshApi.post('api/board', JSON.stringify(board), {
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                }
            });
            if (response.status === 200) {
                alert("작성 완료");
                navigate('/');
            }
            console.log(response);
        } catch (error) {
            alert("작성 실패");
            console.log(error);
        }
      
        // interceptors 적용 전 헤더에 access토큰 요청+게시글 작성 데이터 요청 

        // axios({
        //     url:'api/board',
        //     method: 'POST',
        //     headers: {'Content-Type': 'application/json; charset=utf-8',
        //             // 'access': `${accesstoken}`
        //     },
        //     data:JSON.stringify(board)
        // }).then(res=>{
            
        //     if (res.status === 200 ) {
        //         alert("작성 완료");
        //         Navigate('/');
        //     }
        //     console.log(res);

        // })
        //  .catch(error => {
        //  alert("작성 실패")
        //      console.log(error.res);
        //  }); 
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
