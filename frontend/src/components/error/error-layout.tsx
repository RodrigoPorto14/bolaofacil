const ErrorLayout = ({error, message} : { error : string, message : string }) =>
{
    return(

        <div 
            className="flex flex-col items-center absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-brand-100 rounded-lg text-white px-16 py-8">

            <h1 className="tex text-title text-9xl">{error}</h1>
            <p className="text-4xl">  {message}</p>

        </div>

    )
}

export default ErrorLayout