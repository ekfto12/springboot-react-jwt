import axios from 'axios'

class AuthenticationService {
    executeJwtAuthenticationService(uid, password) {
        return axios.post('http://localhost:8898/auth/login', {
            uid,
            password
        })
    }

    executeRefresh() {
        const token = localStorage.getItem('token');
        const refresh = localStorage.getItem('refreshToken');

        return axios.post('http://localhost:8898/auth/refresh', {
            token,
            refresh
        }).then((response) => {
            this.registerSuccessfullLoginForJwt(response.data.uid, response.data.token, response.data.refreshToken)
        })
    }

    executeHelloService(){
        console.log("===executeHelloService===")
        return axios.get('http://localhost:8898/hello');
    }

    registerSuccessfullLoginForJwt(uid, token, refreshToken) {
        console.log("===registerSuccessfulLoginForJwt===")
        localStorage.setItem('token', token);
        localStorage.setItem('refreshToken', refreshToken);
        localStorage.setItem('authenticatedUser', uid);
        this.setupAxiosInterceptors();
    }

    createJWTToken(token) {
        return 'Bearer ' + token
    }

    setupAxiosInterceptors(){
        axios.interceptors.request.use(
            config => {
                const token = localStorage.getItem('token');
                if (token) {
                    config.headers['Authorization'] = 'Bearer ' + token;
                }
                return config;
            },
            error => {
                Promise.reject(error)
            });
    }

    logout() {
        localStorage.removeItem("authenticatedUser");
        localStorage.removeItem("token");
    }

    isUserLoggedIn(){
        const token = localStorage.getItem('token');
        console.log("===UserloggedInCheck===");
        console.log(token);

        if (token) {
            return true;
        }

        return false;
    }

    getLoggedInUserName() {
        let user = localStorage.getItem('authenticatedUser');
        if(user==null) return '';
        return user;
    }

    
}

export default new AuthenticationService()