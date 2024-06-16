import styles from './CommonSearchBar.module.scss'

function CommonSearchBar() {
  return (
   <div className={styles.searchBar}>
    <div className={styles.searchBar__search}>
        <img src="/src/assets/icons/icon-search.svg"  className={styles.searchBar__search__img} />
        <input type="text"  placeholder='Search ' className={styles.searchBar__search__input}/>
        
    </div>
    <div className={styles.memberdata}>
      
    <span className={styles.memberdata__memberName}>member1</span>
    </div>

   </div>
  )
}

export default CommonSearchBar