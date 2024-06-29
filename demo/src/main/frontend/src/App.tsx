import { BrowserRouter, Route, Routes } from "react-router-dom"
// import { CookiesProvider } from 'react-cookie';
// import TokenRefresh from "./components/common/token/TokenRefresh"
import Main from '@pages/index/index'
import MemberJoin from '@/pages/member/MemberJoin'
import Login from "./pages/login/Login"
import Board from "./pages/board/Board"

function App() {
  
  return (
    // <CookiesProvider>
   <BrowserRouter>
    {/* <TokenRefresh/> */}
    <Routes>
      <Route path='/' element={<Main/>}/> 
      <Route path='/member' element={<MemberJoin/>}/> 
      <Route path='/login' element={<Login/>}/> 
      <Route path='/board' element={<Board/>}/> 
      
    </Routes>
    
    </BrowserRouter>
    // </CookiesProvider>

  )
}

export default App