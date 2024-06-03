import axios from "axios";
import { useState } from "react";
import { Container, Form,Row ,Col,Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
const JoinForm = () => {

    const Navigate = useNavigate();

    const [member,setMember] = useState ({
        username:"",
        name:"",
        password:"",
        hp:"",
        birth:"",
    });

    const changValue = (e) => {
    setMember({
    ...member,
    [e.target.name]:e.target.value
    });
    }
    
   
    
    const joinmember = (e) => {
        e.preventDefault();
        axios({
            url:'http://localhost:3000/member',
            method:'POST',
            headers: {'Content-Type': 'application/json; charset=utf-8'},
            data:JSON.stringify(member)
        }).then(res=>{
            if (res.status === 200 ) {
                alert("회원 가입이 완료 되었습니다.");
                Navigate('/');
            }
            console.log(res);

        })
        .catch(error => {
            alert("이미 존재하는 아이디 입니다.")
            console.log(error.res);
        }); 
    }

    /*const chackid = (e) => {
        axios({
            method: 'get',
            url: '/member/joinproc/'+member.username,
            

        }).then(res=>{
            if (res.data === false) {
                alert("사용 가능한 아이디 입니다.");
                
            }else if(res.data === true){
                alert("이미 존재하는 아이디 입니다.");
                
            }
            console.log(res);

        })
        
    }*/

    return (
        <div>
        <Container className="panel">
            <Form onSubmit={joinmember}>
                <Form.Group as={Row} className="mb-3" controlId="formPlaintextID">
                    <Col sm>
                        <Form.Control type="text" placeholder="아이디" name="username"  onChange={changValue}/>
                    </Col>
                </Form.Group>

                <Button variant="dark">중복 확인</Button>

                <Form.Group as={Row} className="mb-3" controlId="formPlaintextPassword">
                    <Col sm>
                        <Form.Control type="text" placeholder="이름" name="name" onChange={changValue}/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="mb-3" controlId="formPlaintextPassword">   
                    <Col sm>
                        <Form.Control type="text" placeholder="비밀번호" name="password" onChange={changValue}/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="mb-3" controlId="formPlaintextPassword">
                    <Col sm>
                        <Form.Control type="text" placeholder="연락처" name="hp" onChange={changValue}/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="mb-3" controlId="formBasicEmail">
                    <Col sm>
                        <Form.Control type="date"  name="birth" onChange={changValue}/>
                    </Col>
                </Form.Group>
                <br/>

                <div className="d-grid gap-1">
                    <Button variant="secondary" type="submit" >
                        회원가입
                    </Button>
                </div>
            </Form>
        </Container>
    </div>
    );
    
};

export default JoinForm;