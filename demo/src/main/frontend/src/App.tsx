import { BrowserRouter, Route, Routes } from "react-router-dom"
import Main from '@pages/index/index'
import MemberJoin from '@/pages/member/MemberJoin'

function App() {
  
  return (
   <BrowserRouter>
    <Routes>
      <Route path='/' element={<Main/>}/> 
      <Route path='/member' element={<MemberJoin/>}/> 

    </Routes>
    
    </BrowserRouter>
  )
}

export default App