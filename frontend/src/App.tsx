import Routing from "./Routing";
import { AuthProvider } from "./context/AuthProvider";

function App() 

{
  return (
    
    <AuthProvider>
      <Routing />
    </AuthProvider>
    

  );
}

export default App;
