ClassicEditor
    .create( document.querySelector( '#article-body' ), {
        toolbar: [ 'heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', 'blockQuote']
    } )
    .then( newEditor => {
        editor = newEditor;
    })
    .catch( error => {
        console.error( error );
    });
