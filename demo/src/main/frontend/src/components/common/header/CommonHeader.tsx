import styles from './CommonHeader.module.scss'
function CommonHeader() {
  return (
    <div className={styles.header}>
        <div className={styles.header__logoBox}>
            <img src="/src/assets/images/image-logo.png" alt="logo" className={styles.header__logoBox__logo} />
            <span className={styles.header__logoBox__title}>PLANNABLE</span>
        </div>
            <div className={styles.header__profileBox}>
                <button className={styles.header__profileBox__button}>사진 제출</button>
                <button className={styles.header__profileBox__button}>북마크</button>
                <button className={styles.header__profileBox__button}>로그인</button>
                <button className={styles.header__profileBox__button}>회원가입</button>
                <span className={styles.header__profileBox__memberName}>member1 | member1@member.com</span>
            </div>
    </div>
  )
}

export default CommonHeader