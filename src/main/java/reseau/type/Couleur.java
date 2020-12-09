package reseau.type;

public enum Couleur {
    N {
        public String nomEntier(){
            return "NOIR";
        }
    },
    V{
        public String nomEntier(){
            return "VERT";
        }
    },
    B{
        public String nomEntier(){
            return "BLEU";
        }
    },
    R{
        public String nomEntier(){
            return "ROUGE";
        }
    },
    J{
        public String nomEntier(){
            return "JAUNE";
        }
    },
    M{
        public String nomEntier(){
            return "MARRON";
        }
    },
    NUL{
        public String nomEntier(){
            return "NUL";
        }
    };

    public abstract String nomEntier();
}