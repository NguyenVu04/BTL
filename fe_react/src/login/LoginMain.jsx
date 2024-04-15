import './login.css'
import '../assets/style.css'
import study1 from '../assets/img/study1.jpg'
import HCMUT_official_logo from '../assets/img/HCMUT_official_logo.png'

import LoginTeacher from './LoginTeacher'
import LoginOptions from './LoginOptions'
import LoginStudent from './LoginStudent'
function LoginMain () {
    return (
        <div className="container">
            <div className="decoration">
                <h1 className="decoration__heading">Trang đăng nhập</h1>
                <img className="decoration__img" src={study1}/>
            </div>
            <LoginStudent/>
        </div>
    );
}

export default LoginMain