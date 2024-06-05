import CommonHeader  from '@/components/common/header/CommonHeader'
import CommonSearchBar from '@/components/common/searchBar/CommonSearchBar'
import styles from './styles/index.module.scss'

function index() {
  return (
    <div className={styles.page}>
            {/* 공통 헤더 UI 부분 */}
            <CommonHeader/>
            {/* 공통 네비게이션 UI 부분 */}
            
            <div className={styles.page__contents}>
                <div className={styles.page__contents__introBox}>
                    <div className={styles.wrapper}>
                        <span className={styles.wrapper__title}>PLANNABLE</span>
                        <span className={styles.wrapper__desc}>
                        "Plan(계획)"과 "Able(할 수 있는)"의 조합으로, "계획할 수 있는" 또는 "계획이 가능한 "이라는 의미를 담고 있습니다.<br />
                        "PLANNABLE"를 통해 일정을 관리 해보세요!!! 
                        </span>
                        {/* 검색창 UI 부분 */}
                        <CommonSearchBar/>
                    </div>
                </div>
                <div className={styles.page__contents__imageBox}></div>
            </div>
            {/* 공통 푸터 UI 부분 */}
        </div>
  )
}

export default index