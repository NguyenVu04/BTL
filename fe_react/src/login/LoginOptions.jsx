import './login.css'
import '../assets/style.css'
import study1 from '../assets/img/study1.jpg'
import HCMUT_official_logo from '../assets/img/HCMUT_official_logo.png'

function LoginOptions (props) {
    return (
        <div className="login">
            <div className="login__option">
                <div className="option-logo">
                    <img className="login__logo" src={HCMUT_official_logo}/>
                    <h1>HCMUT</h1>
                </div>
                <h3 style={{margin: '20px 20px', textAlign: 'center', lineHeight: '30px'}}>
                    Trường đại học Bách Khoa - Đại học quốc gia TP.HCM <br/>
                    Trường đào tạo kỹ thuật số 1 miền nam
                </h3>
                <div className="login-teacher" onClick={() => props.setOption(2)}>
                    <h3 className="login-note">Đăng nhập cho giảng viên</h3>
                </div>
                <div className="login-student" onClick={() => props.setOption(3)}>
                    <h3 className="login-note">Đăng nhập cho sinh viên</h3>
                </div>
                <p style={{padding: '15px 20px 0px 50px', fontSize: '13px'}}>
                    Bằng cách chọn Đăng nhập, bạn xác nhận việc đã đọc và đồng ý với 
                    Điều kiện & Điều khoản giao dịch cùng Chính sách bảo mật thông tin tại Prepedu.com
                </p>
            </div>
        </div>
    );
}

export default LoginOptions