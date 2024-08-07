import { BrowserRouter, Route, Routes } from "react-router-dom"
import { CookiesProvider } from 'react-cookie';
import Main from '@pages/index/index'
import MemberJoin from '@/pages/member/MemberJoin'
import Login from "./pages/login/Login"
import Board from "./pages/board/Board"
import CommonHeader  from '@/components/common/header/CommonHeader'
import CommonSearchBar from '@/components/common/searchBar/CommonSearchBar'
import My from '@/pages/my/My';
function App() {
  
  return (
    <CookiesProvider>
   <BrowserRouter>
   <CommonSearchBar/>
    <CommonHeader/>
    <Routes>
      <Route path='/' element={<Main/>}/> 
      <Route path='/member' element={<MemberJoin/>}/> 
      <Route path='/login' element={<Login/>}/> 
      <Route path='/board' element={<Board/>}/> 
      <Route path=':id' element={<My/>}/> 

    </Routes>
    
    </BrowserRouter>
    </CookiesProvider>

  )
}

export default App