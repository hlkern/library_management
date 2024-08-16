package com.atmosware.library_project.core.utilities.exceptions.problemDetails;

public class UnexpectedProblemDetails extends  ProblemDetails{

    public UnexpectedProblemDetails(){
        setTitle("Unexpected Violation");
        setType("http://mydomain.com/exceptions/unexpected");
        setStatus("400");
    }
}
