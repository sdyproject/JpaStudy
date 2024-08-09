import React, { useEffect, useState } from 'react'; 
import styles from './My.module.scss'
import refreshApi from '@/components/common/token/refreshApi';


//rfce
interface Member {
  id: number;
  username: string;
}


const MyPage: React.FC = () => {  
  const [member, setMember] = useState<Member>(null);  
  useEffect(() => { 
    const getMemberData = async () => { 
      try {
        const response = await refreshApi.get('api/members/'+member.id); 
        console.info(response);
        setMember(response.data); 
        console.info(response.data);
      } catch (err) {
        console.error("error");
      }
    };

    getMemberData();  
  }, [member.id]);  

  

  if (!member) {  
    return <div>Loading...</div>;  
  }

  return (  
    <div className={styles.my}>
      <div className={styles.wrapper}>
         <div className={styles.wrapper__content}>
      <p className={styles.wrapper__content__username}>{member.username}</p> 
      <p className={styles.wrapper__content__text}>계획한 일정을 확인하세요.</p> 
   
         </div>
      </div>
      <div className={styles.menuwrapper}>
      <div className={styles.menuwrapper__menu}>
            <a href="#home">게시글</a>
            <a href="#about">메이트</a>
            <a href="#services">캘린더</a>
        </div>

        <div className={styles.menuwrapper__c}>
          회원이 작성한 게시글 올 자리 
        </div>

      </div>
      

       
     
      
      
    </div>
  );
};

export default MyPage;  