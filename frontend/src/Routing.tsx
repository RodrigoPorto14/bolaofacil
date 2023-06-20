import { BrowserRouter, Routes, Route, Navigate} from "react-router-dom";
import Home from './pages/home'
import Login from './pages/login'
import Register from './pages/register'
import SweepStakes from "./pages/sweepstakes";
import UpdateSweepstake from "./pages/update-sweepstake";
import Search from "./pages/search";
import ShowResource from "./pages/show-resource";
import UpdateResource from "./pages/update-resource";
import ShowParticipant from "./pages/show-participant";
import ShowRequest from "./pages/show-request";
import { isAuthenticated } from "./utils/auth";
import CreateResource from "./pages/create-resource";

const Routing = () => 
{

  const userAuthenticated = isAuthenticated();

  const sweepstakeConfigPath = (resource : string) => '/sweepstakes/:sweepstakeId/config' + resource

  return (
    <BrowserRouter>
      <Routes>
        
        {
          userAuthenticated ? 
          (
            <>
              <Route path='/sweepstakes' element={<SweepStakes />} />
              <Route path='/search' element={<Search />} />
              <Route path={sweepstakeConfigPath('/sweepstake')} element={<UpdateSweepstake />} />
              <Route path={sweepstakeConfigPath('/participants')} element={<ShowParticipant/>} />
              <Route path={sweepstakeConfigPath('/requests')} element={<ShowRequest/>} />
              <Route path={sweepstakeConfigPath('/rules')} element={<ShowResource resource="regras" />} />
              <Route path={sweepstakeConfigPath('/teams')} element={<ShowResource resource="times" />} />
              <Route path={sweepstakeConfigPath('/matches')} element={<ShowResource resource="partidas" />} />
              <Route path={sweepstakeConfigPath('/rules/create')} element={<CreateResource resource="regras"/>} />
              <Route path={sweepstakeConfigPath('/teams/create')} element={<CreateResource resource="times"/>} />
              <Route path={sweepstakeConfigPath('/matches/create')} element={<CreateResource resource="partidas"/>} />
              <Route path={sweepstakeConfigPath('/rules/:resourceId')} element={<UpdateResource resource="regras" />} />
              <Route path={sweepstakeConfigPath('/teams/:resourceId')} element={<UpdateResource resource="times" />} />
              <Route path={sweepstakeConfigPath('/matches/:resourceId')} element={<UpdateResource resource="partidas" />} />
              
            </>
          ) 
          : 
          (
            <>
              <Route index element={<Home />} />
              <Route path='/login' element={<Login />} />
              <Route path='/register' element={<Register />} />
            </>
          )
        }
        
        <Route path="*" element={<Navigate to={userAuthenticated ? '/sweepstakes' : '/'} replace />} />

      </Routes>
    </BrowserRouter>
  );

};

export default Routing;