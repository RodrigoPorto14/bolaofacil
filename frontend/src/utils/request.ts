import axios, { AxiosRequestConfig } from 'axios';
import qs from 'qs';
import { getToken } from '../context/AuthProvider/auth';
import { createBrowserHistory } from 'history';
import { removeToken } from '../context/AuthProvider/auth';
import { toast } from 'react-toastify';

export type LoginData = 
{
  username: String;
  password: String;
}

const CLIENT_ID = process.env.REACT_APP_CLIENT_ID ?? 'bolaofacil';
const CLIENT_SECRET = process.env.REACT_APP_CLIENT_SECRET ?? '140301ro';
const BACKEND_URL = process.env.REACT_APP_BACKEND_URL ?? 'http://localhost:8080';
export const FRONTEND_URL = process.env.REACT_APP_FRONTEND_URL ?? 'http://localhost:3000';

/****************************************************************** 
Intercepta requisições axios, caso essa requisição tenha sucesso,
apenas retorna a resposta, caso fracasse com erro 401 (Unauthorized)
é feito o logout do usuário
******************************************************************/
axios.interceptors.response.use(
  (response) =>  response, 
  (error) =>
  {
      const history = createBrowserHistory();
      const errorStatus = error.response.status
      if (errorStatus === 401)
      {
        removeToken();
        history.go(0);
      }
      // console.log(error)
      if(errorStatus === 403 || (errorStatus === 404 && !error.response.data.message))
      {
         history.replace('/404');
          history.go(0);
      }

      if(errorStatus === 422)
      {
        const errors = error.response.data.errors;
        if(errors)
            for(let e of errors)
                toast.error(e.message)
      }
      
      return Promise.reject(error);
  }
);

/****************************************************************** 
Realiza requisições axios sem cabeçalho configurado e com a URL base
já inserida
******************************************************************/
export const makeRequest = (params: AxiosRequestConfig) => 
{
  return axios(
  {
    ...params,
    baseURL: BACKEND_URL
  });
}

/****************************************************************** 
Realiza requisições axios com cabeçalho de autorização "Bearer"
configurado e com a URL base já inserida
******************************************************************/
export const makePrivateRequest = (params: AxiosRequestConfig) => 
{
  const token = getToken();

  const headers = {
    'Authorization': `Bearer ${token}`
  }

  return makeRequest({ ...params, headers });

}

/****************************************************************** 
Realiza requisições axios com cabeçalho de autorização "Bearer"
configurado e com a URL base já inserida
******************************************************************/
export const makeUploadRequest = (params: AxiosRequestConfig) => 
{
  const token = getToken();

  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'multipart/form-data'
  }

  return makeRequest({ ...params, headers });

}

/****************************************************************** 
Realiza requisições axios para login, com cabeçalho de autorização 
"Basic" configurado, com a URL da requisição "/oauth/token" já inserida
e com método "POST"
******************************************************************/
export const makeLogin = (loginData: LoginData) => 
{
  const token = `${CLIENT_ID}:${CLIENT_SECRET}`;

  const headers = 
  {
    Authorization: `Basic ${window.btoa(token)}`,
    'Content-Type': 'application/x-www-form-urlencoded'
  }

  const payload = qs.stringify({ ...loginData, grant_type: 'password' });

  return makeRequest({ url: '/oauth/token', data: payload, method: 'POST', headers });

}