import './login.css'
import '../assets/style.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faChevronLeft} from '@fortawesome/free-solid-svg-icons'
import { useState } from 'react'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import validator from "validator";
import { useNavigate } from 'react-router-dom'
function LoginTeacher (props) {
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [disabled, setDisabled] = useState(false)
    const navigate = useNavigate()
    function handleSubmit () {
        if (!email.endsWith('@hcmut.edu.vn') && !validator.isEmail(email)) {
            toast.error("Invalid Email, Please try again.")
            setEmail('')
            setPassword('')
            return;
        }
        if (!validator.isLength(password, {min: 8, max: 32})) {
            toast.error("Invalid Password, Please try again.")
            setEmail('')
            setPassword('')
            return;
        }
        let data = new FormData()
        data.append('email', email)
        data.append('password', password)
        data.append('role', 'TEACHER')
        setEmail('')
        setPassword('')
        setDisabled(true)
        fetch ('http://localhost:8080/login', {
            method: 'POST',
            mode: 'cors',
            body: data
        }).then(
            res => {
                if (!res.ok) {
                    throw new Error("UNAUTHENTICATED")
                }
                return res.json()
            }
        ).then(
            data => {
                localStorage.setItem('Authorization', data['authorization'])
                navigate('/')
            }
        ).catch(
            () => toast.error("Wrong Username or Password, Please try again.")
        ).finally(
            setDisabled(false)
        )
    }
    return (
        <div class="login">
            <div className="login__option-teacher">
                <h2 style={{paddingTop: '30px'}}>
                    <i style={{margin: '0px 70px 0px 40px'}} onClick={() => props.setOption(1)}><FontAwesomeIcon icon={faChevronLeft}/></i>
                    Đăng nhập cho giảng viên
                </h2>
                <p style={{padding: '0px 40px', marginTop: '20px'}}>Lưu ý: Dùng email trường có đuôi hcmut.edu.vn để đăng nhập</p>
                <p className="input-note">Email:</p>
                <input className="input-box" type="email" placeholder="Nhập email" name='email' value={email} onChange={(e) => setEmail(e.target.value)}/>
                <p className="input-note">Mật khẩu:</p>
                <input className="input-box" type="password" placeholder="Nhập mật khẩu" name='password' value={password} onChange={(e) => setPassword(e.target.value)}/>
                <div className="login-button" onClick={() => !disabled && handleSubmit()}>Đăng nhập</div>
                <h3 className="forgotPassword">Quên mật khẩu?</h3>
            </div>
            <ToastContainer />
        </div>
    );
}

export default LoginTeacher