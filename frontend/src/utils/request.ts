import axios, { AxiosRequestConfig } from 'axios';
import qs from 'qs';
import { CLIENT_ID, CLIENT_SECRET, getToken } from '../context/AuthProvider/auth';
import { useAuth } from '../context/AuthProvider/useAuth';

export type LoginData = 
{
  username: String;
  password: String;
}

const BASE_URL = process.env.REACT_APP_BACKEND_URL ?? 'http://localhost:8080';

/****************************************************************** 
Intercepta requisições axios, caso essa requisição tenha sucesso,
apenas retorna a resposta, caso fracasse com erro 401 (Unauthorized)
é feito o logout do usuário
******************************************************************/
/*axios.interceptors.response.use(
  (response) =>  response, 
  (error) =>
  {
    const auth = useAuth();
    if (error.response.status === 401)
      auth.logout()
    
    return Promise.reject(error);
  }
);*/

/****************************************************************** 
Realiza requisições axios sem cabeçalho configurado e com a URL base
já inserida
******************************************************************/
export const makeRequest = (params: AxiosRequestConfig) => 
{
  return axios(
  {
    ...params,
    baseURL: BASE_URL
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