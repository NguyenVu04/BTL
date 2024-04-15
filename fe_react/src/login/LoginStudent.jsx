import './login.css'
import '../assets/style.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faChevronLeft} from '@fortawesome/free-solid-svg-icons'

function LoginStudent () {
    return (
        <div className="login">
            <div className="login__option-student">
                <h2 style={{ paddingTop: '30px' }}>
                <FontAwesomeIcon
                    icon={faChevronLeft}
                    style={{ margin: '0px 70px 0px 40px' }}
                />
                Đăng nhập cho sinh viên
                </h2>
                <p style={{ padding: '0px 70px', marginTop: '20px' }}>
                Lưu ý: Dùng email trường có đuôi hcmut.edu.vn để đăng nhập
                </p>
                <p className="input-note">Email:</p>
                <input className="input-box" type="email" placeholder="Nhập email" />
                <p className="input-note">Mật khẩu:</p>
                <input className="input-box" type="password" placeholder="Nhập mật khẩu" />
                <div className="login-button">Đăng nhập</div>
                <h3 className="forgotPassword">Quên mật khẩu?</h3>
            </div>
        </div>
    );
}

export default LoginStudent