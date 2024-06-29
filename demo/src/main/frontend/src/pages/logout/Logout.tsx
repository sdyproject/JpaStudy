import axios from "axios"
import { Cookies } from 'react-cookie';

const cookies = new Cookies();
function Logout() {

  const refreshToken = cookies.get('refresh');

  axios({
    url:'api/logout',
    method:'POSt',
    headers: { refresh : refreshToken },
  }).then(res=>{
    if(res.status === 200){
      const  access = res.headers.access;
      localStorage.removeItem(access);
    }
  })

}
export default Logout