import jwtDecode from 'jwt-decode';

export const CLIENT_ID = process.env.REACT_APP_CLIENT_ID ?? 'bolaofacil';
export const CLIENT_SECRET = process.env.REACT_APP_CLIENT_SECRET ?? '140301ro';

type LoginResponse = 
{
  access_token: string;
  token_type: string;
  expires_in: number;
  scope: string;
  nickName: string;
  userId: number;
}

type AccessToken = 
{
  exp: number;
  user_name: string;
}

/****************************************************************** 
Salva os dados de autenticação no localStorage
******************************************************************/
export const saveSessionData = (loginResponse: LoginResponse) => 
{
  localStorage.setItem('authData', JSON.stringify(loginResponse));
}

/****************************************************************** 
Obtém os dados de autenticação no localStorage
******************************************************************/
export const getSessionData = () => 
{
  const sessionData = localStorage.getItem('authData') ?? '{}';
  const parsedSessionData = JSON.parse(sessionData);

  return parsedSessionData as LoginResponse;
}

/****************************************************************** 
Obtém o token de acesso dos dados de autenticação e retorna
este token decodificado
******************************************************************/
export const getAccessTokenDecoded = (sessionData : LoginResponse) => 
{
  try 
  {
    const tokenDecoded = jwtDecode(sessionData.access_token);
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
export const isTokenValid = (sessionData : LoginResponse) => 
{
  const { exp } = getAccessTokenDecoded(sessionData);
  return Date.now() <= exp * 1000;
}

/****************************************************************** 
Verifica se o token de acesso contido no localStorage existe e é válido
******************************************************************/
export const isAuthenticated = () => 
{
  const sessionData = getSessionData();
  return !!sessionData.access_token && isTokenValid(sessionData);
}

/****************************************************************** 
Obtém o nome do usuário autênticado
******************************************************************/
export const getAuthenticatedNickName = () =>
{
  const {nickName} = getSessionData()
  return nickName
}

/****************************************************************** 
Remove os dados de autenticação do localStorage
******************************************************************/
export const logout = () =>
{
  localStorage.removeItem('authData');
}