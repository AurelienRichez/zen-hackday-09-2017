const path = require('path')
const ExtractTextPlugin = require('extract-text-webpack-plugin')

const server = path.resolve(__dirname, '../../', 'server')
const output = path.resolve(server, 'public')
const root = path.resolve(__dirname, '..')
const sources = path.resolve(root, 'src')
const context = path.resolve(sources, '.')
const tsconfig = path.resolve(root, 'tsconfig.json')

module.exports = {
  context: context,

  entry: {
    app: './app.ts'
  },

  output: {
    path: output,
    filename: '[name].js'    
  },

  module: {
    loaders: [
      {test: /\.ts(x?)$/, loader: `awesome-typescript-loader?configFileName=${tsconfig}`},
      {test: /\.css$/, loaders: ['style-loader', 'css-loader', 'postcss-loader']},
      {
        test: /\.styl$/,
        loader: ExtractTextPlugin.extract({
          use: [
            {
              loader: 'css-loader',
              query: {modules: true, localIdentName: '[name]-[local]-[hash:base64:5]'}
            },
            {
              loader: 'postcss-loader'
            },
            {
              loader: 'stylus-loader'
            }
          ],
          fallback: 'style-loader'
        }),
        exclude: /node_modules/
      }      
    ]
  },

  resolve: {
    modules: [context, sources, 'node_modules'],
    extensions: ['.js', '.ts', '.tsx']
  },

  plugins: [
    new ExtractTextPlugin('[name].css')
  ],

  devtool: 'source-map'
  
}