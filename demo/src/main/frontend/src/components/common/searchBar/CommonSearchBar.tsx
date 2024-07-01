import styles from './CommonSearchBar.module.scss'
import { useState, useEffect } from 'react';
import refreshApi from "../../../components/common/token/refreshApi";

 function CommonSearchBar() {
  const [memberName, setMemberName] = useState('');

  useEffect(() => {
    const fetchMemberData = async () => {
      try {
        
        const response = await refreshApi.get('api/current-member');
        if (response.status === 200) {
          setMemberName(response.data); 
        }
      } catch (error) {
        console.error('Error fetching member data:', error);
       
      }
    };

    fetchMemberData();
  }, []);


  return (
   <div className={styles.searchBar}>
    <div className={styles.searchBar__search}>
        <img src="/src/assets/icons/icon-search.svg"  className={styles.searchBar__search__img} />
        <input type="text"  placeholder='Search ' className={styles.searchBar__search__input}/>
        
    </div>
    <div className={styles.memberdata}>
  
    <span className={styles.memberdata__memberName}>{memberName}</span>
    </div>

   </div>
  )
}

export default CommonSearchBar
