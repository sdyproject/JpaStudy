import { BrowserRouter, Route, Routes } from "react-router-dom"
import Main from '@pages/index/index'
import MemberJoin from '@/pages/member/MemberJoin'
import Login from "./pages/login/Login"

function App() {
  
  return (
   <BrowserRouter>
    <Routes>
      <Route path='/' element={<Main/>}/> 
      <Route path='/member' element={<MemberJoin/>}/> 
      <Route path='/login' element={<Login/>}/> 
    </Routes>
    
    </BrowserRouter>
  )
}

export default App