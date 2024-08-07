import  { useEffect, useState } from 'react';
import styles from './My.module.scss';
import refreshApi from '@/components/common/token/refreshApi';
import { useParams } from 'react-router-dom';
const My = () => {

  const [member, setMember] = useState(null); // 초기 상태를 undefined로 설정
  const params = useParams();

  useEffect(() => {
    const id = params.id;
    const fetchMemberData = async () => {
      try {

        const response = await refreshApi.get(`/member/${id}`);

        setMember(response.data); // 응답 데이터를 상태에 저장
      } catch (err) {
        alert("회원 정보 없습니다.")
      }
    };

    fetchMemberData();
  }, [params.id]);

  
  if (member === null) {
    // 토큰이 없는 경우
    return <div>로그인 필요</div>;
  }

  return (
    <div className={styles.my}>
      <div className={styles.wrapper}>
        <span className={styles.wrapper__title}>내 정보</span> <br />
        <div className={styles.wrapper__content}>
          {member ? (
            <div>
              <p>이름: {member.username}</p>
              {/* 여기에 회원 정보 필드를 추가로 표시할 수 있습니다 */}
            </div>
          ) : (
            <p>회원 정보를 찾을 수 없습니다.</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default My;
