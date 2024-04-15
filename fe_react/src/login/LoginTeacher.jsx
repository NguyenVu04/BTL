import './login.css'
import '../assets/style.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faChevronLeft} from '@fortawesome/free-solid-svg-icons'
function LoginTeacher () {
    return (
        <div class="login">
            <div className="login__option-teacher">
                <h2 style={{paddingTop: '30px'}}>
                    <i style={{margin: '0px 70px 0px 40px'}}><FontAwesomeIcon icon={faChevronLeft}/></i>
                    Đăng nhập cho giảng viên
                </h2>
                <p style={{padding: '0px 40px', marginTop: '20px'}}>Lưu ý: Dùng email trường có đuôi hcmut.edu.vn để đăng nhập</p>
                <p className="input-note">Email:</p>
                <input className="input-box" type="email" placeholder="Nhập email"/>
                <p className="input-note">Mật khẩu:</p>
                <input className="input-box" type="password" placeholder="Nhập mật khẩu"/>
                <div className="login-button">Đăng nhập</div>
                <h3 className="forgotPassword">Quên mật khẩu?</h3>
            </div>
        </div>
    );
}

export default LoginTeacher