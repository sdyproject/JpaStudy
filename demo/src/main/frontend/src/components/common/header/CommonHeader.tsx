import styles from './CommonHeader.module.scss'
import { Link } from 'react-router-dom';


function CommonHeader() {

   const accesstoken = localStorage.getItem('access');


  return (
    <div className={styles.header}>
      <div className={styles.header__profileBox}>
              <Link to="/" className={styles.header__profileBox__button}>HOME</Link>
              <Link to="/" className={styles.header__profileBox__button}>MATE</Link>
          </div>
        <Link to="/" className={styles.header__logoBox}>
            <img src="/src/assets/images/image-logo.png" alt="logo" className={styles.header__logoBox__logo} />
            <span className={styles.header__logoBox__title}>PLANNABLE</span>
        </Link>
            <div className={styles.header__profileBox}>
                <Link to="/board" className={styles.header__profileBox__button}>BOARD</Link>           
                <Link to="/member" className={styles.header__profileBox__button}>SCHEDULE</Link>
                    {accesstoken ? (
                       <Link to="/my" className={styles.header__profileBox__button}>MY</Link>
                      ) : (
                        <></>) }
            </div>
    </div>
    
  )
}

export default CommonHeader