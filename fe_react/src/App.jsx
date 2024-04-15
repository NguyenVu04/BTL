import LoginMain from "./login/LoginMain.jsx"
import MainPage from "./homepage/MainPage.jsx"
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/login" element={<LoginMain />} />
      </Routes>
    </Router>
  )
}

export default App
