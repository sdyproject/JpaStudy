import React, { useEffect, useState } from 'react'; 
// import styles from './My.module.scss'
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
        const response = await refreshApi.get('api/member'); 
        console.info(response);
        setMember(response.data); 
        console.info(response.data);
      } catch (err) {
        console.error("error");
      }
    };

    getMemberData();  
  }, []);  

  

  if (!member) {  
    return <div>Loading...</div>;  
  }

  return (  
    <div>
      <h1>My Page</h1>  
      <p>Member ID: {member.id}</p>  
      <p>Name: {member.username}</p> 
    </div>
  );
};

export default MyPage;  