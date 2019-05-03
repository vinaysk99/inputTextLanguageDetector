class LanguageDetectionForm extends Component {
    constructor(props) {
        super(props);
        this.state = {value: ''};

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault();
        let data = this.state.value;

        fetch('http://localhost:8080/v1/detectLanguageForText',{
            method: "POST",
            body: JSON.stringify(data),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then(response => {
            response.json().then(data =>{
                let response = JSON.parse(data);
                console.log("Request was successful with response : " + response);
            })
        })
    }

    handleChange(event) {
        this.setState({value: event.target.value});
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Input text to detect its language :
                    <input type="text" name="text" />
                </label>
                <input type="submit" value="Submit" />
            </form>
        );
    }
}