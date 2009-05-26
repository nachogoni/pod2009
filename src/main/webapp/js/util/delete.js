$(document).ready(function() {
  $('.delete').click(function() {
    var answer = confirm("Realmente desea eliminar el elemento seleccionado?");
    return answer;
  }); 
});
