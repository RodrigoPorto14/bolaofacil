import Routing from "./Routing";
import { AuthProvider } from "./context/AuthProvider";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Tooltip } from 'react-tooltip'

function App() 

{
  return (
    <>
      <ToastContainer  />
      <Tooltip className="z-50"id="tooltip"/>

      <div className="min-h-screen w-full flex flex-col">
        <AuthProvider>
          <Routing />
        </AuthProvider>
      </div>
    </>
    

  );
}

export default App;
