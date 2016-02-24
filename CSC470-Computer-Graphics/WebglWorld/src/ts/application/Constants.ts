module constants{
    export class Constants{

        public static get ILLEGAL_ARGUMENTS(){
            return "Illegal arguments";}


        public static get INVALID_MATRIX_FOR_OPERATION(){
            return "Invalid matrix for such operation.";}

        public static get SIZE_MISMATCH():string {return "Size mismatch";}

        public static get UNSUPPORTED_OPERATION():string{
            return "Operation unsupported";
        }

    }
}

export = constants;
