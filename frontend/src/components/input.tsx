type InputProps<T> = 
{
    label: string;
    type: string;
    register: any;
    name: keyof T;
    errors: any;
    defaultValue?: string;
    width?: string;
};
  
  function Input<T>({ label, type, register, name, errors, defaultValue, width='w-full' }: InputProps<T>)
  {
    return (

      <div className={`flex flex-col ${width}`}>

        <label className="px-1">{label}</label>

        <input
          type={type}
          defaultValue={defaultValue}
          className="rounded-lg h-7 px-2"
          {...register(name)}
        />
        
        {errors[name] && <span className="px-1 text-sm text-red-600">{errors[name].message}</span>}
        
      </div>
    );
  }

  export default Input