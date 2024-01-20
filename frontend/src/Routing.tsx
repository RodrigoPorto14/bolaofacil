import { BrowserRouter, Routes, Route, Navigate} from "react-router-dom";
import Home from './pages/home'
import Login from './pages/auth/login'
import Register from './pages/auth/register'
import Search from "./pages/search";
import Sweepstake from "./pages/sweepstake";
import ShowResource from "./pages/show/show-resource";
import UpdateResource from "./pages/update/update-resource";
import ShowParticipant from "./pages/show/show-participant";
import ShowRequest from "./pages/show/show-request";
import CreateResource from "./pages/create/create-resource";
import ConfirmEmail from "./pages/auth/confirm-email";
import ResetPassword from "./pages/auth/password-reset";
import RecoveryPassword from "./pages/auth/password-recovery";
import UpdateUser from "./pages/update/update-user";
import Error404 from "./pages/error404";
import { useAuth } from "./context/AuthProvider/useAuth";
import { ParticipantProvider } from "./context/ParticipantProvider";
import { Resource } from "./utils/enums";

const Routing = () => 
{
  const auth = useAuth();
  const userAuthenticated = auth.userAuthenticated;

  if(!auth.isLoading)
    return (
      <BrowserRouter>
        <Routes>
          {
            userAuthenticated ? 
            <>
                <Route path='/search' element={<Search />} />
                <Route path='/user' element={<UpdateUser />} />
                <Route path='/sweepstakes' element={<ShowResource resource={Resource.SWEEPSTAKE} />} />
                <Route path='/sweepstakes/create' element={<CreateResource resource={Resource.SWEEPSTAKE}/>} />

                <Route path='/sweepstakes/:sweepstakeId/*' element={<ParticipantProvider>
                  <Route index element={<Sweepstake/>} />
                  <Route path='/info' element={<UpdateResource resource={Resource.SWEEPSTAKE} />} />
                  <Route path='/participants' element={<ShowParticipant/>} />
                  <Route path='/requests' element={<ShowRequest/>} />
                  <Route path='/rules' element={<ShowResource resource={Resource.RULE} />} />
                  <Route path='/teams' element={<ShowResource resource={Resource.TEAM} />} />
                  <Route path='/matches' element={<ShowResource resource={Resource.MATCH} />} />
                  <Route path='/rules/create' element={<CreateResource resource={Resource.RULE}/>} />
                  <Route path='/teams/create' element={<CreateResource resource={Resource.TEAM}/>} />
                  <Route path='/matches/create' element={<CreateResource resource={Resource.MATCH}/>} />             
                  <Route path='/rules/:resourceId' element={<UpdateResource resource={Resource.RULE} />} />
                  <Route path='/teams/:resourceId' element={<UpdateResource resource={Resource.TEAM} />} />
                  <Route path='/matches/:resourceId' element={<UpdateResource resource={Resource.MATCH} />} />
                  </ParticipantProvider>
                } />
            </>        
            :
            <>
              <Route index element={<Home />} />
              <Route path='/login' element={<Login />} />
              <Route path='/register' element={<Register />} />
              <Route path='/confirm-email/:token' element={<ConfirmEmail/>} />
              <Route path='/password/reset/:token' element={<ResetPassword/>} />
              <Route path='/password' element={<RecoveryPassword/>} /> 
            </>
          }
       
          <Route path='/404' element={<Error404 />} />
          <Route path="*" element={<Navigate to={userAuthenticated ? '/sweepstakes' : '/'} replace />} />
        </Routes>
      </BrowserRouter>
    );

    return <></>

};

export default Routing;