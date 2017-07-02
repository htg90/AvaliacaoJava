angular.module("App", [])
        .value('urlBase', 'http://localhost:8080/AvaliacaoJava/rest/')
        .controller("ProdutoController", function ($http, urlBase) {
            var self = this;
            self.usuario = "Helbert Trindade Gomes";

            self.produtos = [];

            self.produto = undefined;

            self.filtrar = undefined;

            self.relacionador = undefined;

            self.txtBuscar;

            self.novo = function () {
                self.produto = {};
            };

            self.cancelar = function () {
                self.produto = undefined;
                self.filtrar = undefined;
                self.relacionador = undefined;
                self.atualizarTabela();
            };

            self.salvar = function () {
                var metodo = 'POST';
                if (self.produto.id) {
                    metodo = 'PUT';
                }

                $http({
                    method: metodo,
                    url: urlBase + 'produtos/',
                    data: self.produto
                }).then(function successCallback(response) {
                    self.atualizarTabela();
                }, function errorCallback(response) {
                    self.ocorreuErro();
                });
            };

            self.alterar = function (produto) {
                self.produto = produto;
            };

            self.deletar = function (produto) {
                self.produto = produto;

                $http({
                    method: 'DELETE',
                    url: urlBase + 'produtos/' + self.produto.id + "/"
                }).then(function successCallback(response) {
                    self.atualizarTabela();
                }, function errorCallback(response) {
                    self.ocorreuErro();
                });
            };

            self.relacionar = function () {
                self.produto = undefined;
                self.filtrar = undefined;
                self.produtos = undefined;
                self.relacionador = {};
            };

            self.buscarPorId = function () {
                $http({
                    method: 'GET',
                    url: urlBase + 'produtos/' + self.txtBuscar
                }).then(function successCallback(response) {
                    self.produto = response.data;
                }, function errorCallback(response) {
                    self.ocorreuErro();
                });
            };
            
            self.buscarPorImagem = function () {
                $http({
                    method: 'GET',
                    url: urlBase + 'produtos/id' + self.txtBuscar + "/"
                }).then(function successCallback(response) {
                    self.produtos = response.data;
                    self.produto = undefined;
                }, function errorCallback(response) {
                    self.ocorreuErro();
                });
            };

            self.buscarPorPai = function () {
                $http({
                    method: 'GET',
                    url: urlBase + 'produtos/idParent' + self.txtBuscar + "/"
                }).then(function successCallback(response) {
                    self.produtos = response.data;
                    self.produto = undefined;
                }, function errorCallback(response) {
                    self.ocorreuErro();
                });
            };

            self.ocorreuErro = function () {
                self.produto = undefined;
                alert('Ocorreu um erro inseperado!');
                self.atualizarTabela();
            };

            self.atualizarTabela = function () {
                $http({
                    method: 'GET',
                    url: urlBase + 'produtos/'
                }).then(function successCallback(response) {
                    self.produtos = response.data;
                    self.produto = undefined;
                }, function errorCallback(response) {
                    self.ocorreuErro();
                });
            };

            self.activate = function () {
                self.atualizarTabela();
            };

            self.activate();
        });