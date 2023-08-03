import jwtDecode from 'jwt-decode';

type AccessToken = 
{
  exp: number;
  user_name: string;
}

/****************************************************************** 
Salva o token no localStorage
******************************************************************/
export const saveToken = (token : string) => 
{
  localStorage.setItem('token', token);
}

/****************************************************************** 
Remove o token do localStorage
******************************************************************/
export const removeToken = () => 
{
  localStorage.removeItem('token');
}

/****************************************************************** 
Obtém o token do localStorage
******************************************************************/
export const getToken = () => 
{
  const token = localStorage.getItem('token') ?? '';
  return token;
}

/****************************************************************** 
Obtém o token de acesso dos dados de autenticação e retorna
este token decodificado
******************************************************************/
export const getAccessTokenDecoded = (token : string) => 
{
  try 
  {
    const tokenDecoded = jwtDecode(token);
    return tokenDecoded as AccessToken;
  } 
  catch (error)
  { 
    return {} as AccessToken; 
  }
}

/****************************************************************** 
Verifica se o token de acesso ainda é válido
******************************************************************/
export const isTokenValid = (token : string) => 
{
  const { exp } = getAccessTokenDecoded(token);
  return Date.now() <= exp * 1000;
}

/****************************************************************** 
Verifica se o token de acesso contido no localStorage existe e é válido
******************************************************************/
export const isAuthenticated = () => 
{
  const token = getToken();
  return !!token && isTokenValid(token);
}