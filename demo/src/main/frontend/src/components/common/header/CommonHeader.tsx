import styles from './CommonHeader.module.scss'
import { Link } from 'react-router-dom';


function CommonHeader() {
  

  
  return (
    <div className={styles.header}>
      <div className={styles.header__profileBox}>
              <Link to="/" className={styles.header__profileBox__button}>Home</Link>
              <Link to="/" className={styles.header__profileBox__button}>BOARD</Link>
              <Link to="/" className={styles.header__profileBox__button}>MATE</Link>
          </div>
        <Link to="/" className={styles.header__logoBox}>
            <img src="/src/assets/images/image-logo.png" alt="logo" className={styles.header__logoBox__logo} />
            <span className={styles.header__logoBox__title}>PLANNABLE</span>
        </Link>
            <div className={styles.header__profileBox}>           
                <Link to="/member" className={styles.header__profileBox__button}>SCHEDULE</Link>
                <Link to="/member" className={styles.header__profileBox__button}>JOIN</Link>
                <Link to="/login" className={styles.header__profileBox__button}>LOGIN</Link>    
            </div>
    </div>
    
  )
}

export default CommonHeader