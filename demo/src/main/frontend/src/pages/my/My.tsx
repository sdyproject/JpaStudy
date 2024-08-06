import React from 'react'
import styles from './My.module.scss'

//rfce
function My() {
  return (
    <div className={styles.my}>
         <div className={styles.wrapper}>
            <span className={styles.wrapper__title}>내 정보</span> <br/>
                <div className={styles.wrapper__content}>
                    
                   
                 </div>

          </div>

    </div>
  )
}

export default My