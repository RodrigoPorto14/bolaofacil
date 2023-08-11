import ErrorMessage from "./error-message";

type InputProps<T> = 
{
    label: string;
    type: string;
    register?: any;
    name?: keyof T;
    errors?: any;
    defaultValue?: string;
    width?: string;
    customError?: string
    disabled? : boolean;
};
  
  function Input<T>({ label, type, register, name, errors, defaultValue, width='w-full', customError, disabled}: InputProps<T>)
  {
    return (

      <div className={`flex flex-col ${width}`}>

        <label className="px-1">{label}</label>

        <input
          type={type}
          defaultValue={defaultValue}
          className="rounded-lg h-7 px-2"
          disabled={disabled}
          {...register(name)}
        />
        
        {
          customError ? <ErrorMessage> {customError} </ErrorMessage> :
          errors[name] && <ErrorMessage> {errors[name].message} </ErrorMessage>
        }
        
      </div>
    );
  }

  export default Input