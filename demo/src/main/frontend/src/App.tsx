import { BrowserRouter, Route, Routes } from "react-router-dom"
import Main from '@pages/index/index'
import JoinForm from '@pages/index/JoinForm'
function App() {
  return (
   <BrowserRouter>
    <Routes>
      <Route index path="/" element={<Main/>}></Route>
        <Route path='/joinForm' element={<JoinForm/>}/>  
    </Routes>
    
    </BrowserRouter>
  )
}

export default App