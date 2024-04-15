import './login.css'
import '../assets/style.css'
import study1 from '../assets/img/study1.jpg'
import HCMUT_official_logo from '../assets/img/HCMUT_official_logo.png'

import LoginTeacher from './LoginTeacher'
import LoginOptions from './LoginOptions'
import LoginStudent from './LoginStudent'
import { useState } from 'react'
function LoginMain () {
    const [option, setOption] = useState(1)
    return (
        <div className="container">
            <div className="decoration">
                <h1 className="decoration__heading">Trang đăng nhập</h1>
                <img className="decoration__img" src={study1}/>
            </div>
            {option === 1 && <LoginOptions setOption={setOption}/>}
            {option === 2 && <LoginTeacher setOption={setOption}/>}
            {option === 3 && <LoginStudent setOption={setOption}/>}
        </div>
    );
}

export default LoginMain