import { BrowserRouter, Routes, Route, Navigate} from "react-router-dom";
import Home from './pages/home'
import Login from './pages/login'
import Register from './pages/register'
import Search from "./pages/search";
import Sweepstake from "./pages/sweepstake";
import ShowResource from "./pages/show-resource";
import UpdateResource from "./pages/update-resource";
import ShowParticipant from "./pages/show-participant";
import ShowRequest from "./pages/show-request";
import CreateResource from "./pages/create-resource";
import { useAuth } from "./context/AuthProvider/useAuth";
import { ParticipantProvider } from "./context/ParticipantProvider";

const Routing = () => 
{
  const configPath = (resource : string) => '/config' + resource 
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
                <Route path='/sweepstakes' element={<ShowResource resource="sweepstakes" />} />
                <Route path='/sweepstakes/create' element={<CreateResource resource="sweepstakes"/>} />
                <Route path='/sweepstakes/:sweepstakeId/*' element={<ParticipantProvider>
                  <Route index element={<Sweepstake/>} />
                  <Route path={configPath('/sweepstake')} element={<UpdateResource resource="sweepstakes" />} />
                  <Route path={configPath('/participants')} element={<ShowParticipant/>} />
                  <Route path={configPath('/requests')} element={<ShowRequest/>} />
                  <Route path={configPath('/rules')} element={<ShowResource resource="regras" />} />
                  <Route path={configPath('/teams')} element={<ShowResource resource="times" />} />
                  <Route path={configPath('/matches')} element={<ShowResource resource="partidas" />} />
                  <Route path={configPath('/rules/create')} element={<CreateResource resource="regras"/>} />
                  <Route path={configPath('/teams/create')} element={<CreateResource resource="times"/>} />
                  <Route path={configPath('/matches/create')} element={<CreateResource resource="partidas"/>} />             
                  <Route path={configPath('/rules/:resourceId')} element={<UpdateResource resource="regras" />} />
                  <Route path={configPath('/teams/:resourceId')} element={<UpdateResource resource="times" />} />
                  <Route path={configPath('/matches/:resourceId')} element={<UpdateResource resource="partidas" />} />
                  </ParticipantProvider>
                } />
            </>        
            :
            <>
              <Route index element={<Home />} />
              <Route path='/login' element={<Login />} />
              <Route path='/register' element={<Register />} />
            </>
          }
       
          <Route path="*" element={<Navigate to={userAuthenticated ? '/sweepstakes' : '/'} replace />} />
        </Routes>
      </BrowserRouter>
    );

    return <p>Loading</p>

};

export default Routing;