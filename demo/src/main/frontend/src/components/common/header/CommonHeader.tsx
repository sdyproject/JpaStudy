import styles from './CommonHeader.module.scss'
import { Link } from 'react-router-dom';


function CommonHeader() {
  

  
  return (
    <div className={styles.header}>
        <Link to="/" className={styles.header__logoBox}>
            <img src="/src/assets/images/image-logo.png" alt="logo" className={styles.header__logoBox__logo} />
            <span className={styles.header__logoBox__title}>PLANNABLE</span>
        </Link>
            <div className={styles.header__profileBox}>
              
                <Link to="/member" className={styles.header__profileBox__button}>회원가입</Link>
                <Link to="/login" className={styles.header__profileBox__button}>로그인</Link>
                <span className={styles.header__profileBox__memberName}>member1 | member1@member.com</span>
            </div>
    </div>
    
  )
}

export default CommonHeader